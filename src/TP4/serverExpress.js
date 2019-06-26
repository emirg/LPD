var express = require("express"); // Import del modulo express.js
var app = express();
var http = require("http").Server(app); // Import del servidor http
var io = require("socket.io")(http); // Import del modulo socket.io
var port = process.env.PORT || 3000;

var nombres = new Map(); // Nickname -> SocketID
var clientes = new Map(); // SocketID -> Nickname 


app.use(express.static(__dirname + "/")); // Se establece el path de los archivos estaticos (css, imagenes, etc.)

// Cuando se accede a la pagina envia el html
app.get("/", function (req, res) {
  res.sendFile(__dirname + "/index.html");
});

// Cuando hay una conexion...
io.on("connection", function (socket) {
  socket.on("name", function (name) {
    nombres.set(name, socket.id);
    clientes.set(socket.id, name);
    console.log("Se conecto " + name);
  });

  // Al recibir un mensaje, hacer...
  socket.on("messageTo", function (msg) {
    // Enviar mensaje a todos los nombres menos al emisor
    //console.log(msg);
    var destinatario = nombres.get(msg.to);
    var emisor = clientes.get(socket.id);
    io.to(destinatario).emit('message', { "message": msg.message, "from": emisor });
  });

  socket.on("disconnect", function () {

  });
});

// Inicia el servidor http en el puerto designado
http.listen(port, function () {
  console.log("Server started on: " + port);
});

 // sending to individual socketid (private message)
 //io.to('${socketId}').emit('hey', 'I just met you');
