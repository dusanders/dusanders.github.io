import { Card } from '../../components/card/Card';
import { Typography } from '../../components/typography/Typography';
import './Work.scss';

export function Experience() {
  return (
    <Card title="Experience" className="experience">
      <div>
        <div className="job-title">
          <Typography variant="secondary">
            {`>>  `}
          </Typography>
          <Typography>
            {`Senior Mobile Developer | `}
          </Typography>
          <Typography variant="secondary">
            {`MapYourShow LLC `}
          </Typography>
          <Typography>
            {`[2025 - Present]`}
          </Typography>
        </div>
        <div className="job-desc">

        </div>
      </div>
      <div>
        <div className="job-title">
          <Typography variant="secondary">
            {`>>  `}
          </Typography>
          <Typography>
            {`Senior Mobile/IoT/Full_Stack Developer | `}
          </Typography>
          <Typography variant="secondary">
            {`Fasetto Inc `}
          </Typography>
          <Typography>
            {`[2016 - 2025]`}
          </Typography>
        </div>
        <div className="job-desc">
        </div>
      </div>
    </Card>
  );
}