sending:
   rsocket-cli --dataFormat "application/json" --request -m "org.mvnsearch.account.AccountService.findById"  -i "1" --debug ws://localhost:8088/rsocket

sending2:
   rsocket-cli --dataFormat "application/json" --request -m "org.mvnsearch.account.AccountService.findById.1" -i "" --debug ws://localhost:8088/rsocket
