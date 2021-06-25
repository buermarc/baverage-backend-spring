curl --header "Content-Type: application/json" \
  --request POST \
  --data '{ "name": "A new user with a very special name", "platz_id": 1} ' \
  http://localhost:8080/api/createKunde
