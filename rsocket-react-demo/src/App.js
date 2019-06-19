import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';

import {
    RSocketClient,
    JsonSerializers,
} from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';
import {Flowable, Single} from 'rsocket-flowable';

let index = 0;

class RSocketComponent extends Component {

    state = {
        nick: 'Jackie'
    };

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <a className="App-link"
                       href="http://rsocket.io/"
                       target="_blank"
                       rel="noopener noreferrer">
                        <img src={logo} className="App-logo" alt="logo"/>
                    </a>

                    <p>
                        Welcome {this.state.nick} ！
                    </p>
                </header>
            </div>
        );
    }

    componentDidMount() {
        this.initRsocketWebSocket();
    }

    //use websocket wrapped by rsocket
    initRsocketWebSocket() {
        // Create an instance of a client
        const client = new RSocketClient({
            //serializers: JsonSerializers,
            setup: {
                // ms btw sending keepalive to server
                keepAlive: 60000,
                // ms timeout if no keepalive response
                lifetime: 180000,
                // // format of `data`
                dataMimeType: 'application/json',
                // format of `metadata`
                metadataMimeType: 'x.rsocket.routing.v0',
            },
            transport: new RSocketWebSocketClient({url: 'ws://localhost:8088/rsocket'}),
        });

        // Open the connection
        client.connect().subscribe({
            onComplete: socket => {
                this.socket = socket;
            },
            onError: error => console.error(error),
            onSubscribe: cancel => {/* call cancel() to abort */
            }
        });

        setInterval(() => {
            let that = this;
            this.socket && this.socket.requestResponse({
                data: '' + (++index),
                metadata: 'org.mvnsearch.account.AccountService.findById',
            }).subscribe({
                onComplete(payload) {
                    let account = JSON.parse(payload.data);
                    that.setState({
                        nick: account.nick
                    })
                },
                onError: (e) => {
                    console.log('onError', e)
                }
            });
        }, 2000)

    }

}

export default RSocketComponent;

