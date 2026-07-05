import React from 'react';
import { GitHubCalendar } from 'react-github-calendar';

export const GitHubContributionGrid: React.FC = () => {
  // Explicitly mapping your design tokens to the grid spectrum
  const dashboardGridTheme: any = {
    light: ['#121215', '#064e3b', '#047857', '#10b981', '#34d399'], // System safety fallback
    dark: [
      '#16161a', // Level 0: Background panel line match (Empty days)
      '#022c22', // Level 1: Low velocity
      '#065f46', // Level 2: Moderate velocity
      '#059669', // Level 3: Active build cycle
      '#34d399', // Level 4: Max output / emerald-400 equivalent
    ],
  };

  return (
    <section className="mt-8 border border-zinc-800/60 bg-[#121215]/50 rounded-lg overflow-hidden shadow-xl">
      {/* Instrumentation Header */}
      <div className="border-b border-zinc-800/60 bg-[#16161a] px-6 py-3 flex items-center justify-between">
        <h2 className="text-sm font-bold text-zinc-100 uppercase tracking-wider mono flex items-center gap-2">
          <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span>
          SYS.TELEMETRY // COMMIT_VELOCITY_MATRIX
        </h2>
        <span className="text-xs text-zinc-500 mono">user: dusanders</span>
      </div>

      {/* Grid Canvas */}
      <div className="p-6 bg-[#0c0c0e] flex flex-col items-center justify-center overflow-x-auto selection:bg-emerald-500/20">
        <div className="min-w-[750px] w-full text-zinc-400 mono text-xs custom-calendar-wrapper">
          <GitHubCalendar 
            username="dusanders"
            year={new Date().getFullYear()}
            theme={dashboardGridTheme}
            colorScheme="dark"
            fontSize={11}
            blockSize={11}
            blockMargin={3}
          />
        </div>
      </div>
    </section>
  );
};