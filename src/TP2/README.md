# RMI

## Uso

- Compilar los archivos
  - javac \*.java
- Asociar el registro RMI a un puerto
  - rmiregistry puerto &
- Ejecutar los servidores con sus respectivas IP's y puertos
  - java ServidorPronostico ip puerto
  - java ServidorHoroscopo ip puerto
  - java Servidor ip puerto
- Ejecutar cliente con la consulta como argumentos
  - java Cliente signo dd-mm-aaaa
