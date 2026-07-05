
export interface ArticleProps {
  company: string;
  role: string;
  date: string;
  tag: string;
  responsibilities?: string[];
}

export function Article({ company, role, date, tag, responsibilities }: ArticleProps) {
  return (
      <article className="border border-zinc-800/80 bg-[#121215] p-6 rounded-lg transition-all hover:border-zinc-700/80">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2">
          <div>
            <h3 className="text-lg font-bold text-zinc-100 mono">{company}</h3>
            <p className="text-sm text-zinc-400 mt-0.5">{role}</p>
          </div>
          <div className="text-right sm:text-right text-xs mono text-zinc-500">
            <span>{date}</span>
            <div className="text-emerald-500/90 text-[11px] mt-1">{tag}</div>
          </div>
        </div>
        {responsibilities && responsibilities.length > 0 && (
          <ul className="mt-4 space-y-2 text-sm text-zinc-400 list-none pl-0">
            {responsibilities.map((responsibility, index) => (
              <li key={index} className="relative pl-5 before:content-['▸'] before:absolute before:left-0 before:text-emerald-500">
                {responsibility}
              </li>
            ))}
          </ul>
        )}
      </article>
  );
}