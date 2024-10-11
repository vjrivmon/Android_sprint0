# Proyecto Biometría 3A - Android

## Descripción
Esta app Android se encarga de escanear beacons enviados desde dispositivos Bluetooth Low Energy (BLE). Los datos recogidos, como el Major y Minor de cada beacon, se envían a un servidor REST remoto para su procesamiento y almacenamiento. La aplicación facilita la búsqueda de dispositivos BLE cercanos, muestra información relevante sobre ellos y envía esta información al servidor correspondiente.

## Características
<> *Escaneo de beacons BLE*: Utiliza Bluetooth Low Energy para buscar dispositivos cercanos.
<> *Extracción de información del beacon*: Obtiene datos como UUID, Major, Minor y TxPower de las tramas beacon.
<> *Envío a servidor REST*: Envía los datos recopilados al servidor REST configurado.
<> *Filtro de dispositivos específicos*: Permite buscar beacons específicos basados en su UUID.
<> *Manejo de permisos de Bluetooth*: Solicita permisos necesarios en tiempo de ejecución para evitar problemas con versiones de Android más recientes.

## Instalación
1. Abre el proyecto en Android Studio.
2. Conecta tu dispositivo físico o configura un emulador de Android para pruebas.
3. Asegúrate de tener activados los permisos de *Bluetooth* y *Localización* para la aplicación en tu dispositivo.

## Uso

### Escaneo de Dispositivos BLE
1. Pulsa el botón de *Buscar Dispositivos* en la aplicación.
2. La aplicación comenzará a buscar dispositivos BLE cercanos. 
   - El proceso de escaneo se lleva acabo a través de BluetoothLeScanner y un ScanCallback que ordena y gestiona los resultados.
3. Una vez detectados, los dispositivos BLE aparecerán en pantalla con su información correspondiente (UUID, Major, Minor, TxPower).
4. Los datos recopilados se enviarán automáticamente al servidor REST configurado.


### Guardar Mediciones del sensor
La clase Logica implementa el método guardarMedicion(), que toma un objeto de tipo Medida y lo envía al servidor REST en formato JSON.

### Test de Comunicación REST
Se recomienda implementar pruebas unitarias para verificar la correcta comunicación con el servidor REST y la inserción de datos en un futuro. De momento contamos con un trozo de código de prueba en LogicaRestFake para saber si guarda bien las mediciones.

### Guardar nuevas Medidas
En caso de querer guardar nuevas mediciones, la clase Medidas encapsula los datos capturados por los sensores y es utilizada por la lógica de la aplicación para enviar la información al servidor REST. Los atributos incluyen:
- medicion: Valor de la medición capturado por el sensor.
- tipoSensor: Identificador que indica el tipo de sensor que realizó la medición.
- latitud: Coordenada de latitud de la ubicación donde se capturó la medición.
- longitud: Coordenada de longitud de la ubicación donde se capturó la medición.

#### Métodos de la Clase Medidas:
- getMedicion() / setMedicion(int): Obtiene o establece el valor de la medición.
- getTipoSensor() / setTipoSensor(int): Obtiene o establece el tipo de sensor.
- getLatitud() / setLatitud(double): Obtiene o establece la latitud de la ubicación.
- getLongitud() / setLongitud(double): Obtiene o establece la longitud de la ubicación.

### Filtros de Dispositivos
Para buscar un dispositivo BLE específico, como uno con un UUID definido, puedes usar el método buscarEsteDispositivoBTLE300() que se encarga de comparar el UUID de cada beacon detectado con el UUID objetivo.

### Prueba de que la aplicación funciona correctamente
Sigue estos pasos:
1. Abre el proyecto en Android Studio.
2. Ve a Run > Run Tests para ejecutar todas las pruebas.
3. Asegúrate de tener configurado un emulador o dispositivo físico con los permisos necesarios de Bluetooth y Localización.

