sending:
   rsocket-cli --dataFormat "application/json" --request -m "org.mvnsearch.account.AccountService.findById"  -i "1" --debug ws://localhost:8088/rsocket