import { Article } from "./Article";

export function Work() {
  return (
    <main className="space-y-6">
      <div className="flex items-center justify-between pb-2 border-b border-zinc-800">
        <h2 className="text-xs font-bold mono text-zinc-400 uppercase tracking-widest">Chronological System Ledger</h2>
        <span className="text-[10px] mono text-zinc-600">ORDER: DESC</span>
      </div>

      <Article
        company="MapYourShow LLC"
        role="Senior Mobile Developer"
        date="2025 — PRESENT"
        tag="SYSTEMS OPTIMIZATION"
        responsibilities={[
          "Led the migration of the flagship React Native codebase from legacy JavaScript to modern, type-safe TypeScript.",
          "Architected and optimized the flagship React Native enterprise event engine handled by millions of active attendees.",
          "Established scalable architectural patterns by implementing modular service layers and custom hooks for precise business logic extraction.",
          "Architected robust offline data synchronization workflows leveraging GraphQL with Apollo Client and SQLite local storage."
        ]}
      />

      <Article
        company="AUDIO Cu"
        role="Lead / Senior Mobile Systems Engineer"
        date="2022 — 2024"
        tag="IoT LAYER / HARDWARE"
        responsibilities={[
          "Engineered a high-performance React Native application designed for fine-grained hardware control over custom, next-generation Dolby Atmos IoT speaker systems.",
          "Architected user-facing control systems for real-time hardware manipulation, including interactive speaker placement mapping, custom parametric EQ tuning, and sub-millisecond audio delay routing.",
          "Maintained strict alignment with C-suite product goals and accelerated feature timelines, consistently delivering critical milestones ahead of schedule under high-pressure startup constraints."
        ]}
      />

      <Article
        company="Flysview"
        role="Core Architect & Patent Author"
        date="2020 — 2022"
        tag="REAL-TIME PROTOCOLS"
        responsibilities={[
          "Engineered a patented multi-angle video streaming application (Patent: US-20240056615-A1) using React Native and a custom CameraX implementation to capture synchronous video.",
          "Designed and deployed a proprietary real-time synchronization algorithm utilizing WebRTC and WebSockets to perfectly align multiple concurrent camera feeds under high-concurrency constraints.",
          "Architected a high-throughput backend infrastructure using Node.js, React, and Redis to manage low-latency data streaming and session states across wireless nodes."
        ]}
      />

      <Article
        company="Gravity"
        role="Core Architect & Product Owner"
        date="2019 — 2020"
        tag="IoT / HARDWARE INTEGRATION"
        responsibilities={[
          "Architected an IoT centralization platform using a Node.js and React web application deployed on custom Linux devices to enable seamless multi-device control.",
          "Developed a specialized tablet kiosk application integrated with Android Auto, optimizing the user interface for stable, continuous commercial deployment.",
          "Engineered a comprehensive React Native Android application that served as a central hub for controlling and syncing multiple IoT devices simultaneously.",
          "Prepared and delivered high-stakes technical demonstrations at the Consumer Electronics Show (CES), directly contributing to securing critical venture funding."
        ]}
      />

      <Article
        company="LINK"
        role="Lead / Senior Software Engineer (Mobile & IoT)"
        date="2018 — 2019"
        tag="IoT / HARDWARE INTEGRATION"
        responsibilities={[
          "Architected and integrated RESTful APIs utilizing Node.js to handle secure data streaming and file transactions across wireless nodes.",
          "Customized Linux kernels (LINK OS) for portable, Wi-Fi-enabled IoT storage hardware to handle low-level device operations on custom SoM platforms.",
          "Designed and implemented low-level Linux services to interface directly with hardware components, allowing seamless browser-based management of onboard storage and networking features.",
          "Worked closely with enterprise partners to integrate LINK OS into their existing AOSP systems, ensuring compatibility and optimal performance across diverse hardware configurations."
        ]}
      />

      <Article
        company="Fasetto, Inc."
        role="Senior Software Engineer (Mobile & IoT)"
        date="2017 — 2021"
        tag="MOBILE PEER-TO-PEER"
        responsibilities={[
          "Built peer-to-peer data streaming transport links on top of Xamarin iOS and Android applications, enabling real-time file sharing via BLE and Wi-Fi matrixes.",
          "Shipped full-stack features utilizing .NET Core, Xamarin, and cross-platform native abstractions for real-time chat and file sharing.",
          "Provided support for integrating native Android support packages within Xamarin shared core repository."
        ]}
      />
    </main>
  )
}