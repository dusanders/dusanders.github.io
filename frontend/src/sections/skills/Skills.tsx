
export function Skills() {
  return (
    <section className="border border-zinc-800/80 bg-[#121215] rounded-lg overflow-hidden shadow-xl">
      <div className="bg-[#16161a] border-b border-zinc-800 px-6 py-3">
        <h2 className="text-xs font-bold mono text-zinc-400 uppercase tracking-widest">System Architecture Matrix</h2>
      </div>
      <div className="divide-y divide-zinc-800/60 font-normal">
        <div className="p-4 md:p-6 grid grid-cols-1 md:grid-cols-4 gap-2 md:gap-4">
          <div className="mono text-xs text-emerald-400 font-medium uppercase tracking-wider md:pt-1">01 / Mobile & Native</div>
          <div className="md:col-span-3 text-sm text-zinc-300 leading-relaxed">
            React / React Native, Electron, Expo, Kotlin, Jetpack Compose, CameraX, Android SDK, AOSP.
          </div>
        </div>
        <div className="p-4 md:p-6 grid grid-cols-1 md:grid-cols-4 gap-2 md:gap-4">
          <div className="mono text-xs text-emerald-400 font-medium uppercase tracking-wider md:pt-1">02 / Middleware & Real-Time</div>
          <div className="md:col-span-3 text-sm text-zinc-300 leading-relaxed">
            WebRTC, WebSockets, BLE (Bluetooth Low Energy), Wi-Fi IoT Protocols, Custom Sync Algorithms.
          </div>
        </div>
        <div className="p-4 md:p-6 grid grid-cols-1 md:grid-cols-4 gap-2 md:gap-4">
          <div className="mono text-xs text-emerald-400 font-medium uppercase tracking-wider md:pt-1">03 / Backend & Data</div>
          <div className="md:col-span-3 text-sm text-zinc-300 leading-relaxed">
            Node.js, .NET Core, C#, Redis, PostgreSQL, MongoDB, SQLite, RESTful APIs, GraphQL / Apollo.
          </div>
        </div>
        <div className="p-4 md:p-6 grid grid-cols-1 md:grid-cols-4 gap-2 md:gap-4">
          <div className="mono text-xs text-emerald-400 font-medium uppercase tracking-wider md:pt-1">04 / DevOps & Systems</div>
          <div className="md:col-span-3 text-sm text-zinc-300 leading-relaxed">
            CI/CD Automations, Docker, Bash scripting, Git Core, App Store Connect / Google Play Console pipelines.
          </div>
        </div>
      </div>
    </section>
  )
}