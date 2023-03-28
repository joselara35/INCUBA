import requests
import random
import time

# Definir URL de la API donde se enviarán los datos
API_URL = "http://tu-api.com/datos"

# Función para simular los datos de la incubadora
def obtener_datos():
    # Simular la temperatura entre 35 y 39 grados Celsius
    temperatura = round(random.uniform(35.0, 39.0), 2)
    
    # Simular la humedad entre 50 y 70 por ciento
    humedad = round(random.uniform(50.0, 70.0), 2)
    
    # Simular la cantidad de volteos entre 1 y 10 veces por hora
    volteos = random.randint(1, 10)
    
    # Generar un ID de incubadora aleatorio
    id_incubadora = str(random.randint(1, 100))
    
    # Obtener la fecha y hora actual en formato ISO 8601
    fecha = time.strftime("%Y-%m-%dT%H:%M:%S")
    
 # Imprimir los datos obtenidos en consola
    print("Temperatura: ", temperatura)
    print("Humedad: ", humedad)
    print("Volteos: ", volteos)
    print("ID de incubadora: ", id_incubadora)
    print("Fecha: ", fecha)
    print("-------------------------------------")

    # Retornar los datos como un diccionario
    return {
        "temperatura": str(temperatura),
        "humedad": str(humedad),
        "volteos": str(volteos),
        "id_incubadora": id_incubadora,
        "fecha": fecha
    }

# Función para enviar los datos a la API
def enviar_datos(datos):
    # Realizar la petición POST a la API con los datos en formato JSON
    respuesta = requests.post(API_URL, json=datos)
    
    # Verificar si la petición fue exitosa
    if respuesta.status_code == 200:
        print("Datos enviados correctamente:", datos)
    else:
        print("Error al enviar los datos:", respuesta.status_code)

# Bucle principal para tomar y enviar los datos cada 5 minutos
while True:
    # Obtener los datos de la incubadora
    datos = obtener_datos()
    
    # Enviar los datos a la API
    # enviar_datos(datos)
    
    # Esperar 5 minutos antes de tomar los siguientes datos
    time.sleep(10)
