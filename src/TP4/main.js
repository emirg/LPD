// Cuando se termina de cargar la pagina, se inicializa todo
window.addEventListener("load", function (event) {
  var socket = io(); // Socket.io

  // Al recibir un mensaje, crea un elemento en la lista chat mostrando dicho mensaje
  socket.on("message", function (msg) {
    
    var liMessageRecibed=document.createElement('li');
    liMessageRecibed.className="messageReceived";

    var divFrom=document.createElement('div');
    divFrom.className="formatFrom";

    var divMessage=document.createElement('div');
    divMessage.className="formatMessage";

    var textMessage=document.createTextNode(msg.message);
    var textFrom=document.createTextNode(" "+msg.from+":");

    divFrom.innerHTML=('<i class="fas fa-user-alt"></i>')
    divFrom.appendChild(textFrom);
    divMessage.appendChild(textMessage)

    liMessageRecibed.append(divFrom);
    liMessageRecibed.append(divMessage);

    $("#chat").append(liMessageRecibed);

   
  });

  // Cuando se presiona el boton de 'Send', se envia el mensaje
  $("#sendButton").on("click", function () {
    sendMessage();
  });

  // Cuando se apreta 'enter', se envia el mensaje
  $("#messageToSend").keypress(function (e) {
    if (e.which == 13) {
      sendMessage();
    }
  });

  // Cuando se apreta 'enter', se asigna el nuevo nick
  $("#nickname").keypress(function (e) {
    if (e.which == 13) {
      var to = $("#nickActual").text();
      if (to == "Nick:") {
        var name = $("#nickname")
          .val()
          .trim();
        if (!name) {
          return false;
        }
        socket.emit("name", name);
        $("#nickname")
          .val("");
        $("#nickActual").text(name);
      }
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
    var name = $("#nickname")
      .val()
      .trim();
    socket.emit("messageTo", { "message": message, "to": name });
    $("#messageToSend")
      .val("")
      .focus();
    $("#chat").append($('<li class="messageSended">').text(message)); 
    // Agrega el mensaje a la lista del chat como un mensaje enviado por el cliente, y no recibido del servidor
   
   
    $("#contenedor_chat").stop().animate({ scrollTop: $("#contenedor_chat")[0].scrollHeight }, 1000);

    return false;
  }



});



