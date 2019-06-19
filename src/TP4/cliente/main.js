// Initialize everything when the window finishes loading
window.addEventListener("load", function(event) {
  var status = document.getElementById("status");
  var url = document.getElementById("url");
  var open = document.getElementById("open");
  var close = document.getElementById("close");
  var send = document.getElementById("send");
  var message = document.getElementById("message");
  var titulo = document.getElementById("titulo");
  var socket;
  status.textContent = "Not Connected";
  url.value = "ws://localhost:8080";

  //  url.value =  document.URL;
  close.disabled = true;
  send.disabled = true;

  // Create a new connection when the Connect button is clicked
  open.addEventListener("click", function(event) {
    open.disabled = true;
    socket = new WebSocket(url.value, "echo-protocol");
    socket.addEventListener("open", function(event) {
      close.disabled = false;
      send.disabled = false;
      status.textContent = "Connected";
    });

    // Display messages received from the server
    socket.addEventListener("message", function(event) {
      //console.log("RECIBI ALGO");
      //console.log(event);
      //console.log(event.data);
      titulo.innerText = event.data;
    });

    // Display any errors that occur
    socket.addEventListener("error", function(event) {
      message.textContent = "Error: " + event;
    });

    socket.addEventListener("close", function(event) {
      open.disabled = false;
      status.textContent = "Not Connected";
    });
  });

  // Close the connection when the Disconnect button is clicked
  close.addEventListener("click", function(event) {
    close.disabled = true;
    send.disabled = true;
    message.textContent = "";
    socket.close();
  });

  // Send text to the server when the Send button is clicked
  send.addEventListener("click", function(event) {
    message = document.getElementById("message").value;
    socket.send(message);
  });
});
