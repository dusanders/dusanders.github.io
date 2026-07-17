import React from 'react';

interface Project {
  title: string;
  description: string;
  tags: string[];
  githubUrl: string;
  liveUrl?: string;
  badge?: string;
}

const featuredProjects: Project[] = [
  {
    title: "ARK Config Editor",
    description: "Browser-based React app for uploading, editing, and exporting ARK: Survival Ascended Game.ini and GameUserSettings.ini files with helper tools for NPC replacement and difficulty overrides.",
    tags: ["TypeScript", "React", "Vite", "INI Editor"],
    githubUrl: "https://github.com/dusanders/ark-config-editor",
    liveUrl: "https://dusanders.github.io/ark-config-editor/",
    badge: "Latest"
  },
  {
    title: "Forza Utils",
    description: "Expo and Electron-based cross-platform utility suite for Forza Motorsport / Horizon games. Features a telemetry dashboard, live map overlay, and real-time vehicle data visualization.",
    tags: ["React Native", "Websockets", "SQLite", "Expo", "Node.js", "Electron"],
    githubUrl: "https://github.com/dusanders/forzautils_reactnative", 
    badge: "Real-Time Telemetry"
  },
  {
    title: "ForzaTelemetryAPI (TypeScript)",
    description: "TypeScript implementation of a telemetry data parser and API for Forza Motorsport / Horizon games. Provides structured access to real-time vehicle data, enabling developers to build custom telemetry applications.",
    tags: ["TypeScript", "Node.js", "Buffer Parsing", "API"],
    githubUrl: "https://github.com/dusanders/ForzaTelemetryAPI_typescript",
    badge: "Java / Kotlin to TypeScript"
  },
];

export const GitHubProjects: React.FC = () => {
  return (
    <section className="mt-8 border border-zinc-800/60 bg-[#121215]/50 rounded-lg overflow-hidden shadow-xl">
      {/* Header Panel */}
      <div className="border-b border-zinc-800/60 bg-[#16161a] px-6 py-3 flex items-center justify-between">
        <h2 className="text-sm font-bold text-zinc-100 uppercase tracking-wider mono flex items-center gap-2">
          <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span>
          SYS.REPOSITORIES // KEY_PROJECTS
        </h2>
        <a 
          href="https://github.com/dusanders" 
          target="_blank" 
          rel="noopener noreferrer"
          className="text-xs text-zinc-400 hover:text-emerald-400 transition-colors mono"
        >
          view_all_git ↗
        </a>
      </div>

      {/* Projects Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 divide-y md:divide-y-0 md:divide-x divide-zinc-800/60 bg-[#0c0c0e]">
        {featuredProjects.map((project, idx) => (
          <div key={idx} className="p-6 flex flex-col justify-between hover:bg-[#121215]/30 transition-all group">
            <div>
              <div className="flex items-start justify-between gap-2">
                <h3 className="text-base font-bold text-zinc-100 mono group-hover:text-emerald-400 transition-colors">
                  {project.title}
                </h3>
                {project.badge && (
                  <span className="text-[10px] px-2 py-0.5 rounded border border-emerald-500/30 text-emerald-400 font-semibold uppercase tracking-widest mono bg-emerald-500/5">
                    {project.badge}
                  </span>
                )}
              </div>
              <p className="text-sm text-zinc-400 mt-3 leading-relaxed">
                {project.description}
              </p>
            </div>

            <div className="mt-6 pt-4 border-t border-zinc-800/40">
              {/* Tech Stack Sub-cluster */}
              <div className="flex flex-wrap gap-1.5 mb-4">
                {project.tags.map((tag, tIdx) => (
                  <span key={tIdx} className="text-[11px] px-2 py-0.5 bg-[#16161a] text-zinc-400 rounded border border-zinc-800/60 mono">
                    {tag}
                  </span>
                ))}
              </div>

              {/* Interactive Actions */}
              <div className="flex gap-4 text-xs mono">
                <a 
                  href={project.githubUrl} 
                  target="_blank" 
                  rel="noopener noreferrer"
                  className="text-zinc-500 hover:text-zinc-200 transition-colors"
                >
                  [ source_code ]
                </a>
                {project.liveUrl && (
                  <a 
                    href={project.liveUrl} 
                    target="_blank" 
                    rel="noopener noreferrer"
                    className="text-emerald-400/90 hover:text-emerald-400 transition-colors"
                  >
                    [ deployment ]
                  </a>
                )}
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};