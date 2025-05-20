<h1 align="center"> Software Engineering 2024-2025 </h1>
<sh align="center"> Prova Finale - Ingegneria del Software </sh>

## Authors (Group 6)
- [Elisa Garsetti](https://github.com/elisagarsetti124)
- [Nicolò Pallavicini](https://github.com/NicoPallavicini)
- [Dmitrii Meshcheriakov](https://github.com/Dima765Me)
- [Luca Papiro](https://github.com/hash-cartographer)
## Implementation of Galaxy Trucker
### Project specification
Galaxy Trucker is a board game which stands for the Final Test of the Software Engineering BSc course of PoliMi CompSci
### Implemented Functionalities
|       Functionalities        | Status |
|:----------------------------:|:------:|
|       Simplified Rules       | 🛠️ |
|        Complete Rules        | ✅ |
|            Socket            | 🔬 |
|             RMI              | ✅ |
|             TUI              | 🔬 |
|             GUI              | 🛠️ |
|         Test Flight          | 🛠️ |
| Resilience to disconnections | 🔬 |
|       Multiple Matches       | ❌ |
|         Persistence          | ❌ |
###### Legend: ❌ Not Implemented   ✅ Implemented   🛠️ In progress   🔬 Testing
<!--## Documentation
### Test Coverage
You can check up our JUnit test coverage [here](Deliverables%2FTEST%20COVERAGE%2FtestCoverage.png).-->
<!--### UML
Here you can check our UML diagrams:
- [High level UML Server-Client](Deliverables%2FUMLs%2FUML%20ALTO%20LIVELLO.png)
- [Detailed UML Server-Client](Deliverables%2FUMLs%2FUML%20DETTAGLIO.png)
- [Communication Protocol Diagrams](Deliverables%2FNETWORK%20SEQUENCE%20DIAGRAMS)-->
<!--### JavaDoc
You can read the JavaDoc [here](Deliverables%2FJavaDoc).-->
<!--### Jar
You can download the Jar to launch the game [here](Deliverables%2FJAR).
## How to run
### Server
1. Open the prompt as **administrator**.
2. Deactivate your antivirus and firewall services 
[```BE CAREFUL! Do that only in a safe network```]
```bash
netsh advfirewall set allprofiles state off
```
3. Allow the prompt to show more characters in a single row: ```Prompt``` ➡ ```(Right click) Properties``` ➡ ```Layout``` ➡ ```Deselect "Text output wraps when resizing"``` ➡ ```Setting "9000" as width``` ➡ ```OK```
4. Launch jar file using the following command (**pay attention to your directory**): 
```bash
java -jar pathToServerLauncher\ServerLauncher.jar
```
5. Insert Server Ip address or press enter for localhost.
6. When finished playing, remember to reactivate your security settings:
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
3. Set the registry of Windows to let it recognise colors using the following command:
```bash
reg add HKEY_CURRENT_USER\Console /v VirtualTerminalLevel /t REG_DWORD /d 1
```
4. Allow the prompt to show more characters in a single row: ```Prompt``` ➡ ```(Right click) Properties``` ➡ ```Layout``` ➡ ```Deselect "Text output wraps when resizing"``` ➡ ```Setting "9000" as width``` ➡ ```OK```
5. Launch jar file using the following command (**pay attention to your directory**):
```bash
java -jar pathToClientLauncher\ClientLauncher.jar
```
6. Insert Server Ip address or press enter for localhost.
7. Select the communication protocol and the UI preferred.
8. _(Only for RMI)_ Insert Client Ip address or press enter for localhost.
9. When finished playing, remember to reactivate your security settings:
```bash
netsh advfirewall set allprofiles state on
```

## How to play
1. When you launch the clientJar you will be able to choose your preferred network protocol (RMI/TCP) and user Interface (TUI/GUI).
2. You will then proceed with the nickname selection.
3. After that you will be asked if you want to create a new lobby or join an already existing one.
4. The game will start when the correct number of connected clients is reached.


-->
