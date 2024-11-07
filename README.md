# Sales Reporting System

A microservice-based sales reporting system that generates scheduled reports based on sales data. The system features
asynchronous, event-driven communication using Kafka, enabling scalability and flexibility.

Services include:

- Scheduler Service: Triggers report creation at set intervals.
- Report Service: Generates reports by combining sales data and templates.
- Notification Service: Sends report completion notifications.
- Sales Service: Retrieves and provides the necessary sales data.

![alt text](https://github.com/muhammedkurtulus/sales-reporting/blob/main/architecture.png?raw=true)

Run Apache Kafka using Docker: <br/>

```
docker run -d -p 9092:9092 --name broker apache/kafka:latest
```
