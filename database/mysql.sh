podman run -it --name our-mysql -p 127.0.0.1:3306:3306 -v $(pwd)/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=1234 mysql:latest
