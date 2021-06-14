curl --header "Content-Type: application/json" \
  --request POST \
  --data '{ "bestellungs_id": "1"} ' \
  http://localhost:8080/api/setBestellungsStatusVorbereitet
