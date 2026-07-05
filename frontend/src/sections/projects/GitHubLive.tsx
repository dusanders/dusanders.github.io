import React, { useEffect, useState } from 'react';

interface GitHubRepo {
  id: number;
  name: string;
  description: string;
  html_url: string;
  topics: string[];
  stargazers_count: number;
  language: string;
}

export const GitHubLive: React.FC = () => {
  const [repos, setRepos] = useState<GitHubRepo[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('https://api.github.com/users/dusanders/repos?sort=updated&per_page=50')
      .then(res => res.json())
      .then((data: GitHubRepo[]) => {
        // Filter down to only repositories you labeled with the 'portfolio-feature' topic on GitHub
        // const featured = data.filter(repo => repo.topics?.includes('portfolio-feature'));
        setRepos(data);
        setLoading(false);
      })
      .catch(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="mt-8 border border-zinc-800/60 bg-[#121215]/50 p-6 rounded-lg text-center mono text-xs text-zinc-500">
        INITIALIZING REPO_STREAM...
      </div>
    );
  }

  return (
    <section className="mt-8 border border-zinc-800/60 bg-[#121215]/50 rounded-lg overflow-hidden">
      <div className="border-b border-zinc-800/60 bg-[#16161a] px-6 py-3">
        <h2 className="text-sm font-bold text-zinc-100 uppercase tracking-wider mono">
          // LIVE_REPOSITORY_TELEMETRY
        </h2>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 p-4 bg-[#0c0c0e]">
        {repos.map(repo => (
          <a 
            key={repo.id} 
            href={repo.html_url}
            target="_blank"
            rel="noopener noreferrer"
            className="p-4 border border-zinc-800/60 rounded bg-[#121215]/20 hover:border-emerald-500/40 hover:bg-[#121215]/50 transition-all flex flex-col justify-between"
          >
            <div>
              <div className="flex justify-between items-center">
                <span className="text-sm font-bold text-zinc-200 mono">{repo.name}</span>
                <span className="text-xs text-zinc-500 mono">★ {repo.stargazers_count}</span>
              </div>
              <p className="text-xs text-zinc-400 mt-2 line-clamp-2">{repo.description}</p>
            </div>
            <div className="mt-4 flex justify-between items-center text-[11px] mono text-zinc-500">
              <span>lang: {repo.language || 'TypeScript'}</span>
              <span className="text-emerald-400/80">view_repo ↗</span>
            </div>
          </a>
        ))}
      </div>
    </section>
  );
};