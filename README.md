Drones controller backend service , this is backend application provides rest webservices APIs
to define drones and control it to move medications from stock to customers , simply if we will
provide mobile app or web app to integrate with this backend application end users will be
able to register drones to system , send order to registered drones to load shipment of medication
and send it to customer's GPS coordination. system take care about check ability of drone to
hold medication depends on weight limit of drone and weight of medication also check drone's
battery capacity before start trip to move shipment.

System developed based on Springboot
consist of services are :

- Drone service :
  manage drone data serial number , limit weight , model and battery capacity. retrieve drones
  data check available drones to start trips also you can charge drone from this service.

- Cron service:
  there's a one cron job run every one min (configurable) to get all data about drones batteries capacity and save them
  as a historical data.

- Medication service :
  manage medications data and their weights.

- Order service :
  manage orders of drones create an order for a drone to load a medication , get order history
  hint :- after creating order there's a simulation function starts in thread mode to calculate distance between
  stock GPS coordination (configurable) to customer GPS coordination in KM then after
  drone start its trip the battery capacity of it will be decreased by 2 % per 1 km
  which changes also the state of drone starting from IDLE -> IN_PROGRESS -> DELIVERED.

  —--------------------------------------------------------------------------------------------------------------------------------------

Installation :-

Software needed : JDK 22 , maven

Steps .
Clone the whole project then run

Run
mvn clean install

Start (Dev Mode) for Code Review

public APIs for Test and Examples :

	-hint : Core service provided by swagger
		http://localhost:8080/swagger-ui/index.html

Core Services

- Drone

    - Create drone

      http://localhost:8080/drones/register (POST)

      {
      "serialNumber": "1A",
      "model": "LIGHTWEIGHT",
      "weightLimit": 100,
      "batteryCapacity": 70
      }

    - Update drone

      http://localhost:8080/drones/update (PUT)

      {
      "id": 1,
      "serialNumber": "A2",
      "model": "LIGHTWEIGHT",
      "weightLimit": 40,
      "batteryCapacity": 30
      }

    - Get drone
      http://localhost:8080/drones/{id}/ (GET)

        -       http://localhost:8080/drones/1/

    - Get available drone for shipment process (all drones in IDLE state)
      
      http://localhost:8080/drones/get-available (GET)

    - Get all drones in db

      http://localhost:8080/drones/ (GET)

    - Delete drone (DELETE)

      http://localhost:8080/drones/{id}/
      
        -          http://localhost:8080/drones/1/
          
- Medication
    - Save or update medication
      - validations
        - Medication name is mandatory.
        - Medication name must be letters with _ or -.
        - Medication code is mandatory.
        - Medication code must be uppercase with _ or -.
        
       http://localhost:8080/medications/ (POST) - form data
        
        medication : "{   
                 "name" : "Medication",
                 "weight" : 90,
                 "code" : "TK1"
                     }"

        image : file (multipart)
  
    - Retrieve medication (image will be in binary)
        http://localhost:8080/medications/{id} (GET)
         -       http://localhost:8080/medications/1
          
    - Retrieve all medications (image will be in binary)
        http://localhost:8080/medications/ (GET)

    - Delete medication
        http://localhost:8080/medications/{id} (DELETE)

- Orders
  - create order and start order
    - http://localhost:8080/orders/save (POST)
      {
      "customerName": "Ahmed",
      "droneId": 1,
      "medicationId": 1,
      "latitude": 42.6703217,
      "longitude": 23.3484632,
      }
  
  - get current running orders by drones
    - http://localhost:8080/orders/getCurrentOrders (GET)
  
  - get all orders
    - http://localhost:8080/orders/

- History
  - Get all records created by cron job that log drones batteries every 1 min
    - http://localhost:8080/history/getAll




-   Properties
    stock.location.latitude (drones stock location)
    stock.location.longitude (drones stock location)
    battery.cron.check.schedule= (interval time for cron job)
    drone.battery.trip.limit=(drone battery limit for start order)
    
    

    
    

