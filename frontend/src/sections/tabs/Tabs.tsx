
export function Tabs() {
  return (
    <section className="grid grid-cols-1 sm:grid-cols-3 gap-4">
      <div className="border border-zinc-800/80 bg-[#121215]/50 p-4 rounded-lg text-center sm:text-left">
        <div className="text-[10px] mono text-zinc-500 uppercase tracking-wider">Patent Author</div>
        <div className="text-lg font-semibold text-zinc-200 mono mt-1">US-20240056615-A1</div>
        <div className="text-xs text-zinc-500 mt-1">Multi-angle video synchronization</div>
      </div>
      <div className="border border-zinc-800/80 bg-[#121215]/50 p-4 rounded-lg text-center sm:text-left">
        <div className="text-[10px] mono text-zinc-500 uppercase tracking-wider">Hardware Integration</div>
        <div className="text-lg font-semibold text-zinc-200 mono mt-1">CES Demoed Tech</div>
        <div className="text-xs text-zinc-500 mt-1">Gravity Streaming Platform</div>
      </div>
      <div className="border border-zinc-800/80 bg-[#121215]/50 p-4 rounded-lg text-center sm:text-left">
        <div className="text-[10px] mono text-zinc-500 uppercase tracking-wider">Startup Velocity</div>
        <div className="text-lg font-semibold text-zinc-200 mono mt-1">10+ Years Experience</div>
        <div className="text-xs text-zinc-500 mt-1">Shipped from core architecture to store</div>
      </div>
    </section>
  )
}