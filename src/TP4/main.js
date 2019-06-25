// Cuando se termina de cargar la pagina, se inicializa todo
window.addEventListener("load", function(event) {
  var socket = io(); // Socket.io

  // Al recibir un mensaje, crea un elemento en la lista chat mostrando dicho mensaje
  socket.on("message", function(msg) {
    $("#chat").append($('<li class="messageReceived">').text(msg));
  });

  // Cuando se presiona el boton de 'Send', se envia el mensaje
  $("#sendButton").on("click", function() {
    sendMessage();
  });

  // Cuando se apreta 'enter', se envia el mensaje
  $("#messageToSend").keypress(function(e) {
    if (e.which == 13) {
      sendMessage();
    }
  });

  // Funcion utilizada para obtener el mensaje escrito y enviarlo al servidor
  function sendMessage() {
    var message = $("#messageToSend")
      .val()
      .trim();
    if (!message) {
      return false;
    }
    socket.emit("message", message);
    $("#messageToSend")
      .val("")
      .focus();
    $("#chat").append($('<li class="messageSended">').text(message)); // Agrega el mensaje a la lista del chat como un mensaje enviado por el cliente, y no recibido del servidor
    $("#contenedor_chat").stop().animate({ scrollTop: $("#contenedor_chat")[0].scrollHeight}, 1000);

    return false;
  }



});



