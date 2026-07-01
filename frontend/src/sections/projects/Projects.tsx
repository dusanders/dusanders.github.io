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
    title: "Flysview Architecture",
    description: "Patented real-time multi-angle video streaming system. Implemented custom synchronization algorithms utilizing React Native, CameraX, WebRTC, and Redis to solve concurrent stream alignment.",
    tags: ["React Native", "CameraX", "WebRTC", "Redis", "Node.js"],
    githubUrl: "https://github.com/dusanders/flysview-core", // Replace with actual links
    badge: "PATENTED"
  },
  {
    title: "Gravity IoT Centralization",
    description: "An IoT multi-device control hub deployed on custom Linux clients. Features a type-safe React/Node web dashboard paired with an Android kiosk interface for low-latency hardware synchronization.",
    tags: ["React", "Node.js", "TypeScript", "Android SDK", "AOSP"],
    githubUrl: "https://github.com/dusanders/gravity-iot"
  },
  {
    title: "AI Retrieval / Semantic Search Playground",
    description: "Experimental local-first dashboard built to explore modern embedding models and vector search optimization. Tailored with high-performance UI layers to visualize dataset reranking states.",
    tags: ["TypeScript", "React", "Vector DB", "LLM Integration"],
    githubUrl: "https://github.com/dusanders/ai-retrieval-ui",
    badge: "AI EXPERIMENT"
  }
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