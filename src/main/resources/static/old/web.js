let ws_address = location.origin.replace(new RegExp('https?'), 'ws') + "/web-socket"; 
let socket = new WebSocket(ws_address);

let count = 1;

console.log("test");
socket.addEventListener('message', function (event) {
    console.log('Message from server ', event.data);
    document.querySelector("#A").textContent = "NewText: " + count;
    count++;
});

socket.addEventListener('open', function (_event) {
    socket.send("The client says bonjour");
});


