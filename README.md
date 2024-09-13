# Sales Reporting System
A microservice-based sales reporting system that generates scheduled reports based on sales data. The system features asynchronous, event-driven communication using Kafka, enabling scalability and flexibility. 

Services include:

- Scheduler Service: Triggers report creation at set intervals.
- Report Service: Generates reports by combining sales data and templates.
- File Storage Service: Saves reports to object storage and generates access links.
- Notification Service: Sends report completion notifications.
- Sales Service: Retrieves and provides the necessary sales data.

Run Apache Kafka using Docker: <br/>

```
docker run -d -p 9092:9092 --name broker apache/kafka:latest
```
