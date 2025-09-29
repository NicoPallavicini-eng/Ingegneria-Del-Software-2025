<h1 align="center"> Software Engineering 2024-2025 </h1>
<h3 align="center"> Prova Finale - Ingegneria del Software - Gruppo 6 </h3>

Gioco distribuito da Cranio Creations, questa repository fa parte del progetto finale di ingegneria del software presso il Politecnico di Milano è stata creata con i soli scopi universitari. 

## Authors
- [Elisa Garsetti](https://github.com/elisagarsetti124)
- [Nicolò Pallavicini](https://github.com/NicoPallavicini)
- [Dmitrii Meshcheriakov](https://github.com/Dima765Me)
- [Luca Papiro](https://github.com/hash-cartographer)

## Implementation

### Project specification
*Galaxy Trucker* is a digital adaptation of the board game, developed as the final project for the Software Engineering BSc course at PoliMi (Computer Science).

### Implemented Functionalities

| Functionality                 | Status |
|------------------------------|---------|
| Simplified Rules             |   ✅   |
| Complete Rules               |   ✅   |
| Socket Support               |   ✅   |
| RMI Support                  |   ✅   |
| Text-Based UI (TUI)          |   ✅   |
| Graphical UI (GUI)           |   ✅   |
| Test Flight Mode             |   ❌   |
| Disconnection Resilience     |   ✅   |
| Multiple Matches             |   ❌   |
| Persistence (Save System)    |   ❌   |

#### Legend
- ❌ Not Implemented
- ✅ Implemented

## Documentation
### Test Coverage
Testing coverage of MODEL: 72%
### UML
You can check our UML diagrams [here](Deliverables/Final/UML).
### JavaDoc
You can read the JavaDoc [here](Deliverables/Final/JavaDOC/apidocs).
### Jar
You can download the Jar to launch the game [here](Deliverables/Final/Jar).
## How to run
### Server
1. Open the prompt as **administrator**.
2. Deactivate your antivirus and firewall services 
[```BE CAREFUL! Do that only in a safe network```]
```bash
netsh advfirewall set allprofiles state off
```
3. Open prompt normally and continue there
4. Launch jar file using the following command (**pay attention to your directory**): 
```bash
java -jar pathToServerLauncher\ServerLauncher.jar
```
5. When finished playing, remember to reactivate your security settings:
```bash
netsh advfirewall set allprofiles state on
```
### Client
1. Open the prompt as **administrator**.
2. Deactivate your antivirus and firewall services
      [```BE CAREFUL! Do that only in a safe network```]
```bash
netsh advfirewall set allprofiles state off
```
3. Open prompt normally (not as administrator) and continue there
4. Launch jar file using the following command (**pay attention to your directory**):
```bash
java -jar pathToClientLauncher\ClientLauncher.jar
```
5. Select the communication protocol and the UI preferred.
6. Insert Server Ip address or press -1 for localhost.
7. When finished playing, remember to reactivate your security settings:
```bash
netsh advfirewall set allprofiles state on
```

## How to play
1. When you launch the clientJar you will be able to choose your preferred network protocol (RMI/TCP) and user Interface (TUI/GUI).
2. You will then proceed with the nickname selection and max number of players selection (if needed).
3. The game will start when the correct number of connected clients is reached.
