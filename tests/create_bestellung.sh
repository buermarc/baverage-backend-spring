curl --header "Content-Type: application/json" \
  --request POST \
  --data '{ "platz_id": 1, "getraenk_id": 1, "kunde_id": 1 } ' \
  http://localhost:8080/api/createBestellung
