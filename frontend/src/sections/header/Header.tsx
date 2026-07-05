
export function Header() {
  return (
    <header className="border border-zinc-800/80 bg-[#121215] p-6 md:p-8 rounded-lg shadow-2xl relative overflow-hidden">
      <div className="absolute top-0 right-0 p-3 text-[10px] mono text-zinc-600 tracking-widest uppercase select-none hidden sm:block">
        LOCATION: MINNESOTA, USA
      </div>

      <div className="flex flex-col md:flex-row md:justify-between md:items-start gap-6">
        <div>
          <h1 className="text-3xl md:text-4xl font-bold text-zinc-100 tracking-tight mono">DUSTIN ANDERSON</h1>
          <p className="text-emerald-400 font-medium mono text-sm mt-2 tracking-wide uppercase">
            Senior Mobile & IoT Systems Engineer
          </p>
          <div className="flex flex-wrap gap-4 mt-4 text-xs mono text-zinc-400">
            <a href="mailto:dusanders@gmail.com" className="hover:text-emerald-400 transition-colors flex items-center gap-1">
              <span>[e]</span> dusanders@gmail.com
            </a>
            <a href="https://linkedin.com/in/dus-anders" target="_blank" className="hover:text-emerald-400 transition-colors flex items-center gap-1">
              <span>[l]</span> linkedin.com/in/dus-anders
            </a>
            <a href="https://github.com/dusanders" target="_blank" className="hover:text-emerald-400 transition-colors flex items-center gap-1">
              <span>[g]</span> github.com/dusanders
            </a>
          </div>
        </div>
      </div>

      <div className="mt-8 border-t border-zinc-800/60 pt-6">
        <p className="text-zinc-400 leading-relaxed max-w-3xl">
          I architect high-performance native and cross-platform mobile ecosystems that interface directly with complex IoT hardware, custom backends, and low-latency streaming networks. My true passion lies in the <strong className="text-zinc-200 font-semibold">middle layer and backend infrastructure</strong>—building robust data synchronization layers, optimizing raw performance, and engineering custom communication protocols where there is zero margin for error.
        </p>
      </div>
    </header>
  )
}