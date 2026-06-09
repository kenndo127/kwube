# Kwube — Igbo Voice-Powered AI Personal Assistant

> **Kwube** (Igbo: *speak* / *continue speaking*) is a conversational AI assistant built specifically for Igbo speakers. It understands natural Igbo text, identifies user intent, executes real tasks using live data, and responds in Igbo with both text and speech.

---

## Table of Contents

* [The Problem](#the-problem)
* [Features](#features)
* [What Kwube Does](#what-kwube-does)
* [Architecture](#architecture)
* [Tech Stack](#tech-stack)
* [Quick Start](#quick-start)
* [API](#api)
* [Testing](#testing)
* [Deployment](#deployment)
* [Known Limitations](#known-limitations)
* [Roadmap](#roadmap)
* [The Bigger Vision](#the-bigger-vision)
* [License](#license)
* [Author](#author)

---

## The Problem

Over 45 million Igbo-speaking Nigerians interact daily with technology built primarily around dominant world languages. Voice assistants, banking apps, and smart devices rarely support Igbo — not because the demand is absent, but because the language lacks the data infrastructure and commercial investment available to major languages.

Most systems that claim Igbo support rely on fixed commands and pre-recorded responses. They do not understand natural language; they simply recognize predefined patterns. Once a user moves beyond those patterns, the experience breaks down.

Kwube is built to change that.

---

## Features

* 🌦 **Real-time weather updates** — Ask about the weather in any city using Igbo
* 💱 **Live currency conversion** — Query exchange rates and currency values in natural Igbo
* 💬 **Natural conversation** — Ask questions and receive responses entirely in Igbo
* 🔊 **Speech output** — Every response is converted into spoken audio
* ⚡ **Live data integration** — Responses are generated using real-time external services
* 🛡 **Robust error handling** — Structured API responses with appropriate HTTP status codes
* 📖 **Interactive API documentation** — Swagger UI included

---

## What Kwube Does

A user sends a message in Igbo.

Kwube:

1. Translates and interprets the request
2. Detects the user's intent
3. Executes the appropriate task
4. Generates a natural Igbo response
5. Converts the response to speech
6. Returns both the text and audio output

### Example

**Input**

```text
Gwa m ihe naira iri dị na dollar
```

**Output**

```json
{
  "igboText": "Gwa m ihe naira iri dị na dollar",
  "translation": "Tell me what ten naira is in dollars",
  "intention": "The user wants to know the exchange rate between Naira and Dollar",
  "result": "Naira iri hà bụ dollar 0.0065",
  "audio": "<Base64 encoded MP3>"
}
```

🔊 Spoken Igbo audio is generated alongside the text response.

---

## Architecture

```text
User (Igbo Text)
       ↓
REST API (Spring Boot)
       ↓
Gemini AI
(Translation + Intent Detection)
       ↓
Intent Router
       ├── Weather Intent      → WeatherAPI
       ├── Currency Intent     → ExchangeRate API
       └── Conversation Intent → Gemini AI
       ↓
Gemini AI
(Response Generation in Igbo)
       ↓
ElevenLabs TTS
       ↓
Text + Audio Response
```

---

## Tech Stack

| Layer             | Technology              |
| ----------------- | ----------------------- |
| Backend           | Java 21, Spring Boot    |
| AI / NLP          | Google Gemini 2.5 Flash |
| Text-to-Speech    | ElevenLabs              |
| Weather Data      | WeatherAPI              |
| Currency Data     | ExchangeRate API        |
| HTTP Clients      | Spring Cloud OpenFeign  |
| API Documentation | SpringDoc OpenAPI       |
| Configuration     | Spring Dotenv           |
| Build Tool        | Maven                   |

---

## Quick Start

### Prerequisites

* Java 21
* Maven
* API keys for:

    * Google Gemini
    * WeatherAPI
    * ExchangeRate API
    * ElevenLabs

### Installation

Clone the repository:

```bash
git clone https://github.com/kenndo127/kwube.git
cd kwube
```

Create a `.env` file in the project root using the provided `.env.example` template and populate the required API keys.

Run the application:

```bash
mvn spring-boot:run
```

Open Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

If you can submit a request and receive a response, the setup is complete.

---

## API

### POST `/api/v1/kwube`

Send an Igbo query and receive a text response with generated speech.

### Request

```json
{
  "igboText": "Kedụ ka weather dị na Lagos?"
}
```

### Response

```json
{
  "igboText": "Kedụ ka weather dị na Lagos?",
  "translation": "How is the weather in Lagos?",
  "intention": "The user wants to know the current weather conditions in Lagos.",
  "result": "Igwe dị mma na Lagos taa, okpomoku ya bụ iri na abụọ degree Celsius.",
  "audio": "<Base64 encoded MP3>"
}
```

### Error Responses

| Status | Description              |
| ------ | ------------------------ |
| 400    | Invalid or empty request |
| 502    | External service failure |
| 500    | Internal server error    |

Interactive documentation is available through Swagger UI when running locally.

---

## Testing

Recommended manual test cases:

### Weather

```text
Kedụ ka weather dị na Lagos?
```

### Currency

```text
Gwa m ihe naira iri dị na dollar
```

### Conversation

```text
Kedu?
```

### Error Handling

```json
{}
```

Expected result: `400 Bad Request`

---

## Deployment

Deployment is in progress. The application will be hosted on a cloud platform supporting Spring Boot JAR deployments.

Environment variables will be configured through the platform dashboard — no `.env` file is uploaded to any server.

---

## Known Limitations

### ElevenLabs Igbo Pronunciation

ElevenLabs currently does not provide a dedicated Igbo voice model. Audio output remains understandable but pronunciation quality is not yet native-level.

### Gemini Igbo Consistency

Gemini occasionally defaults to English for certain informational responses. Kwube mitigates this through a dedicated response translation step.

### Intent Routing

The current implementation uses a simple intent router suitable for a prototype. A more scalable Strategy-based architecture is planned for future versions.

---

## Roadmap

| Phase   | Description                                                      | Status         |
| ------- | ---------------------------------------------------------------- | -------------- |
| Phase 1 | Web prototype with weather, currency, and conversational support | 🚧 In Progress |
| Phase 2 | Android application with reminders, alarms, and device actions   | 🔜 Planned     |
| Phase 3 | API platform for banks, fintechs, and customer service systems   | 🔜 Planned     |

---

## The Bigger Vision

Every interaction with Kwube is a potential contribution to the future of Igbo language technology.

As the platform grows, it becomes a source of naturally occurring Igbo language data that can help power better language models, speech systems, and AI products for African languages.

Kwube is not just a personal assistant.

It is infrastructure for Igbo-language voice interaction and a step toward closing the data gap that has historically excluded low-resource African languages from modern AI systems.

---

## License

MIT License. See the `LICENSE` file for details.

---

## Author

**Okechukwu Kenneth Chidiebube**

Computer Science Student | Backend Developer | NLP Enthusiast

GitHub: [kenndo127](https://github.com/kenndo127)

LinkedIn: [Kenneth Okechukwu](https://linkedin.com/in/kenneth-okechukwu)