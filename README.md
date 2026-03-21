# 📋 ToDoMaster - Office Tool for Task Management

![Language](https://img.shields.io/badge/Language-Java%2017+-orange) ![Maven](https://img.shields.io/badge/Build-Maven-blue) ![Architecture](https://img.shields.io/badge/Architecture-MVC-purple) ![Tests](https://img.shields.io/badge/Tests-JUnit-green)

**TodoMaster** is a desktop **task management application** built with **Java Swing** using a clean **MVC architecture** and several classic design patterns. It allows users to organise tasks into checklists, manage priorities and deadlines, and persist data between sessions using JSON storage.

The project demonstrates:
- object-oriented design
- decoupled MVC architecture
- testable controller design
- reactive UI updates via the Observer pattern

---
## ✨ Key Features

- Workspace containing multiple checklists
- Task management with:
	- title and description
	- priority (`HIGH` / `MEDIUM` / `LOW`)
	- optional deadline
	- completion status
- Task sorting by:
	- importance
	- deadline
	- priority
	- creation date
	- lexicographic order
- Two UI display modes:
	- Card view
	- Flowing text view
- Automatic data persistence using JSON
- Auto-save on application exit
- Unit-tested controller and model logic

---
## 📸 Screenshots / Demonstration

### Workspace Page
![Workspace](docs/screenshots/workspace.png)

### Checklist Page in Card View
![Checklist Cards](docs/screenshots/checklist_as_cards.png)

### Checklist Page in Text View
![Checklist Text](docs/screenshots/checklist_as_text.png)

---
## 🚀 Running the Application

#### Requirements:
- Java **17+**
- Maven

#### Build from Repository Root:
```shell
mvn clean package
```

#### Run from Repository Root:
```shell
mvn exec:java -Dexec.mainClass="Main"
```

---
## 🏗 Architecture

The application follows a **Model–View–Controller (MVC)** architecture.

```mermaid
graph LR
    View["View (Swing UI)"]
    Controller["Controller<br/>(AppContoller, PageControllers)"]
    Model["Model<br/>(Workspace, Checklist, Task)"]

    View -->|user input| Controller
    Controller -->|updates state| Model
    Model -->|notifies observers| View
```

### UML Diagram

A detailed UML diagram illustrating the architecture is available at [**`docs/uml/class_diagram.png`**](docs/uml/class_diagram.png).

⚠️ *Note: The UML may slightly differ from the latest refactoring (interface-based decoupling).*

![UML Diagram](docs/uml/class_diagram.png)

### Key Design Patterns

- **MVC** – separation of UI, logic, and data
- **Observer Pattern** – model changes update the UI automatically
- **Singleton Pattern** – single global `App` instance
- **Factory-like UI composition** – dynamic panel generation
- **Strategy-like sorting** – different task sorting modes

---
## 🗃️ Project Structure

```text
src
│
├── main/java
│   │
│   ├── App.java				→ Composition root (application wiring)
│   ├── Main.java				→ Entry point
│   │
│   ├── controller/
│   │   ├── AppController       		→ Navigation implementation
│   │   ├── WorkspacePageController
│   │   ├── ChecklistPageController
│   │   └── contracts/          		→ Controller abstractions
│   │       ├── IAppNavigator
│   │       ├── IUserDialogService
│   │       ├── IWorkspacePageListener
│   │       └── IChecklistPageListener
│   │
│   ├── model/                 → Core domain logic
│   │   ├── Workspace
│   │   ├── Checklist
│   │   ├── Task
│   │   ├── AbstractData
│   │   └── AbstractCollection
│   │
│   ├── view/                  → UI components (Swing)
│   │   ├── pages/
│   │   ├── panels/
│   │   ├── dialogs/
│   │   └── controls/
│   │
│   ├── auxiliaries/           → Utilities
│   │   ├── JsonFileStorage
│   │   ├── TaskSorter
│   │   └── FontLoader
│   │
│   └── resources/
│       ├── data/exemplary.json
│       └── fonts/
│
└── test/java
    │
    ├── controller/            				→ Controller unit tests
    │   ├── ChecklistPageControllerTests
    │   ├── WorkspacePageControllerTests
    │   ├── FakeDialogService      			→ Test double for dialogs
    │   └── FakeAppNavigator       			→ Test double for navigation
    │
    ├── model/                 → Model tests
    ├── auxiliaries/           → Utility tests
    └── view/                  → View logic tests
```

Additional directories:

```text
docs
├── apidocs/		→ .html documentation generated via javadoc
├── screenshots/	→ screenshots to demonstrate user interface
└── uml/      		→ UML diagram for the project
```

---
## 💾 Data Persistence

Application data is automatically saved to `~/toDoMaster-data.json`. If no data file exists on startup, the application loads an example workspace from the bundled resource `src/main/resources/data/exemplary.json`.

---
## 🧪 Testing

The project includes a unit test suite built with **JUnit 5**.

What is tested:
- Model logic (Workspace, Checklist, Task)
- Sorting logic (TaskSorter)
- Controller behavior:
  - checklist operations
  - workspace interactions
  - navigation triggers
  - View interaction logic via listener abstractions

Instead of mocking complex UI-heavy classes, the project uses:
- `MockDialogService` → simulates user input
- `MockAppNavigator` → captures navigation calls

#### Run tests:
```shell
mvn test
```

---
## 📖 Documentation

Full API documentation is generated via:

```shell
mvn javadoc:javadoc
```

Then all documenting files are available under `docs/apidocs/index.html`.

---
## 🎯 What This Project Demonstrates

This project showcases:

- Clean object-oriented design
- MVC architecture in desktop applications
- Observer pattern for reactive UI updates
- Swing UI component composition
- JSON-based persistence
- Separation of domain model, UI, and controllers
- Modular and maintainable project structure

It serves as a portfolio project demonstrating practical software engineering principles and architectural patterns in Java desktop application development.

---
## 🙌 Get Involved

Feel free to:

- Clone or fork the repository
- Explore the codebase and project architecture
- Study the implemented design patterns
- Extend the application with new features
- Use the project as inspiration for your own Java Swing or MVC-based applications

---
### Thanks for Visiting!

I hope this project serves both as a learning resource and a demonstration of practical software engineering concepts such as MVC architecture, design patterns, and clean application structure.

Happy coding! 🚀
