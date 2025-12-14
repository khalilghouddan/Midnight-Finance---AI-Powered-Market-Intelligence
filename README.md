# 🦅 Midnight Finance - AI-Powered Market Intelligence

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple) ![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-blue) ![Gemini AI](https://img.shields.io/badge/Generative%20AI-Gemini%20Pro-orange) ![Architecture](https://img.shields.io/badge/Architecture-MVVM-green)

**Midnight Finance** is a premium Android application designed to provide real-time financial news and deep market analysis powered by **Google's Gemini AI**. Built with modern Android technologies, it offers a sleek, "Dark Mode" first experience for investors and traders.

## 🎥 App Demo

[![Demo Video](https://drive.google.com/file/d/1_3oE9GyK3rIoMHGHD0MkxqHVQoAGHqvT/view?usp=sharing)](https://drive.google.com/file/d/1_3oE9GyK3rIoMHGHD0MkxqHVQoAGHqvT/view?usp=sharing)



---

## ✨ Key Features

### 1. 📰 Real-Time Financial News
Stay ahead of the market with instant updates.
- **Provider**: [GNews API](https://gnews.io/)
- **Features**: Fetches the latest headlines on stocks, crypto, and global markets.
- **UI**: Modern, glass-morphic cards with live update indicators.

### 2. 🧠 AI-Powered Analysis
Don't just read the news; understand it.
- **Integration**: **Gemini Pro** (via Google Generative AI SDK).
- **Function**: Select any article, and the AI will generate a comprehensive **financial report**, summarizing key points, bullish/bearish signals, and market impact.
- **Architecture**: Asynchronous analysis ensures the UI remains responsive.

### 3. 💬 AI Financial Advisor
Your personal pocket analyst.
- **Feature**: A dedicated chat interface to ask questions like *"What is the outlook for Tesla?"* or *"Explain short selling"*.
- **Tech**: Utilizes Gemini's conversational capabilities with context-aware prompts.
- **UI**: Premium chat interface with gradient bubbles and Markdown support for rich text responses.

### 4. 🔒 The Vault (Local Storage)
Save your insights for later.
- **Tech**: **Room Database** (SQLite).
- **Function**: Persist important AI analyses locally on your device. Access them offline anytime.

---

## 🛠️ Technical Architecture & Gemini Integration

### Gemini Integration Journey 🚀
This project demonstrates a robust integration of Generative AI into a mobile context:

1.  **SDK Implementation**: Uses `com.google.ai.client.generativeai` for direct, efficient communication with Gemini Pro.
2.  **Structured Prompting**: Custom prompt engineering ensures the AI acts specifically as a "Financial Analyst," avoiding generic responses.
3.  **Security Best Practice (Recommended)**:
    *   While the app works directly with the SDK, a production-grade deployment recommends using **Firebase Extensions** or a backend proxy to handle API keys securely.
    *   *Firebase acts as a secure intermediary layer, preventing API key exposure in the client APK.*

### Tech Stack
*   **Language**: Kotlin
*   **UI Framework**: Jetpack Compose (Material 3)
*   **Network**: Retrofit + Gson
*   **Local Data**: Room Database
*   **Concurrency**: Coroutines & Flow

---

## 🚀 Getting Started

### Prerequisites
1.  Android Studio Hedgehog or newer.
2.  **API Keys**:
    *   `GNEWS_API_KEY`: Get from [gnews.io](https://gnews.io).
    *   `GEMINI_API_KEY`: Get from [aistudio.google.com](https://aistudio.google.com).

### Setup
1.  Clone the repository.
2.  Open `GeminiApiImpl.kt` and `GNewsApiImpl.kt`.
3.  Insert your API keys into the constants (or set up `local.properties` for security).
4.  Build and Run on an Emulator or Physical Device.

### Design Philosophy
The app follows a **"Midnight Finance"** design language:
*   **Deep Blue Backgrounds** (`#0F172A`) for reduced eye strain and premium feel.
*   **Electric Blue Accents** (`#38BDF8`) for readability and action.
*   **Fluid Typography** using modern sans-serif fonts.

---

*Project developed for the Kotlin & AI Module - UniCA / EMSI.*
