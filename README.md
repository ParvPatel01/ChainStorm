# ⛓️ ChainStorm

**ChainStorm** is a blockchain prototype built using **Scala** and **Akka** that leverages actor-based concurrency to simulate decentralized data storage and mining. It provides an educational and efficient model of blockchain functionality, focusing on proof-of-work, transaction management, and asynchronous communication.

> 🧪 Built as part of an academic project by [Parv Patel](https://github.com/ParvPatel01)

---

## 📌 Overview

Unlike traditional blockchain implementations that rely on synchronous and resource-intensive mechanisms, **ChainStorm** improves efficiency by using Akka actors to enable **asynchronous**, **scalable**, and **parallel** task execution. This helps improve transaction throughput and node coordination.

---

## 📦 Features

- ✅ Actor-based **Proof-of-Work mining**
- ✅ Fully-functional **Blockchain model**
- ✅ Simulated **decentralized network** with nodes and broker
- ✅ REST API using **Akka HTTP**
- ✅ Modular actor system: Node, Blockchain, Miner, Client, and Broker
- ✅ SHA-256 hashing and block validation

---

## 🔨 Technologies Used

- **Scala** – Core implementation
- **Akka Actor & Cluster** – Concurrency and messaging
- **Akka HTTP** – REST APIs
- **ScalaTest** – Unit testing framework

---

## 🧠 Architecture & Actors

### 🧱 Blockchain Actor
- Maintains the blockchain
- Validates and adds blocks
- Responds to chain queries

### 👤 Node Actor
- Coordinates mining, transactions, and communication
- Interfaces with Broker, Miner, and Blockchain actors

### 💰 Miner Actor
- Performs proof-of-work mining
- Validates nonce and block difficulty
- Coordinates readiness to mine

### 🔄 Broker Actor
- Manages transaction pool
- Synchronizes transactions across nodes
- Clears pool after successful mining

### 🧾 Client/User Actor
- Simulates transaction requests from users

---

## 📂 Block Structure

Each block contains:
- `index`: Position in the chain
- `previousHash`: For integrity
- `timestamp`: Creation time
- `data`: Transactions
- `hash`: SHA-256 hash of the block
- `nonce`: Proof of work

---

## 📅 Development Timeline

| Week | Milestone |
|------|-----------|
| Week 1 | Blockchain Data Structure |
| Week 2 | Blockchain Actor Model |
| Week 3 | Miner & Proof-of-Work |
| Week 4 | Node & Client Actors |
| Week 5 | Testing & Debugging |
| Week 6 | Deployment |

---

## 🧪 Acceptance Criteria

✅ Transactions can be created and propagated  
✅ Broker maintains accurate transaction pool  
✅ Miner computes valid proof-of-work  
✅ Blockchain actor adds validated blocks  
✅ Nodes can request and mine blocks  

---

## 🔁 Sample API Endpoints (via Akka HTTP)

| Method | Endpoint              | Description                     |
|--------|-----------------------|---------------------------------|
| GET    | `/chain`              | Get the full blockchain         |
| POST   | `/transactions/new`   | Submit a new transaction        |
| GET    | `/mine`               | Trigger block mining            |
| GET    | `/peers`              | View connected nodes            |

---

## 🚀 Running the Project

### Requirements
- Java 8+
- Scala
- sbt

### Run Locally
```bash
sbt compile
sbt run
