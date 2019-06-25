var express = require("express"); // Import del modulo express.js
var app = express();
var http = require("http").Server(app); // Import del servidor http
var io = require("socket.io")(http); // Import del modulo socket.io
var port = process.env.PORT || 3000;

app.use(express.static(__dirname + "/")); // Se establece el path de los archivos estaticos (css, imagenes, etc.)

// Cuando se accede a la pagina envia el html
app.get("/", function(req, res) {
  res.sendFile(__dirname + "/index.html");
});

// Cuando hay una conexion...
io.on("connection", function(socket) {
  // Al recibir un mensaje, hacer...
  socket.on("message", function(msg) {
    // Enviar mensaje a todos los clientes menos al emisor
    socket.broadcast.emit("message", msg);
  });
});

// Inicia el servidor http en el puerto designado
http.listen(port, function() {
  console.log("Server started on: " + port);
});
