sending:
   rsocket-cli --metadataFormat routing --dataFormat json --request -m "org_mvnsearch_account_AccountService_findById"  -i "1" --debug ws://localhost:8088/rsocket

sending2:
   rsocket-cli --metadataFormat routing --dataFormat json --request -m "org_mvnsearch_account_AccountService_findById_1" -i "{}" --debug ws://localhost:8088/rsocket

benchmarking_rpc:
  wrk -t5 -c50 -d30s --latency http://localhost:8080/user/2
