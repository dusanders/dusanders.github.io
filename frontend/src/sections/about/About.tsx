import './About.scss';
import { ILocaleContext } from "../../context/locale/Locale";
import { Typography } from "../../components/typography/Typography";
import { Card } from "../../components/card/Card";

export interface AboutProps {
  locale: ILocaleContext;
}


export function AboutSection(props: AboutProps) {
  const formatTitle = (title: string) => {
    return `*${title.toUpperCase()}:>_ `
  }
  return (
    <Card id="about"
      className="about-root"
      title={props.locale.strings.aboutSection.title}>
      <div className="content">
        <div>
          <Typography variant="faded">
            Senior Software Engineer | Mobile & IoT Architect | Systems Engineering
          </Typography>
        </div>
        <div className="row">
          <div className="title">
            <Typography variant="bold">
              {formatTitle("WHAT_I_DO")}
            </Typography>
          </div>
          <div className="body">
            <Typography variant="faded">
              {` I architect high-performance cross-platform and native mobile ecosystems (React Native, Kotlin, Java) that interface directly with complex IoT hardware, custom backends (Node.js), and low-latency streaming networks.`}
            </Typography>
          </div>
        </div>
        <div className="row">
          <div className="title">
            <Typography variant="bold">
              {formatTitle("MY_CORE_APPROACH")}
            </Typography>
          </div>
          <div className="body">
            <Typography variant="faded">
              {` I design and implement robust data synchronization layers, handle hardware/device integration, profile memory management, and engineer custom communication protocols where there is zero margin for error.`}
            </Typography>
          </div>
        </div>
        <div className="row">
          <div className="title">
            <Typography variant="bold">
              {formatTitle("TRACK_RECORD")}
            </Typography>
          </div>
          <div className="body">
            <Typography variant="faded">
              {` Over 10 years of startup execution. Secured a patent for multi-angle video synchronization via custom WebRTC/WebSockets, built BLE/Wi-Fi communication systems for enterprise IoT devices demoed at CES, and modernized legacy event data-pipelines to efficiently process high-concurrency real-time event traffic.`}
            </Typography>
          </div>
        </div>
        <div className="row">
          <div className="title">
            <Typography variant="bold">
              {formatTitle("MY_CORE_PASSION")}
            </Typography>
          </div>
          <div className="body">
            <Typography variant="faded">
              {`I thrive in the middle layer and backend infrastructure—building robust data synchronization layers, handling hardware/device integration, profiling memory management, and engineering custom communication protocols where there is zero margin for error.`}
            </Typography>
          </div>
        </div>
      </div>
    </Card>
  )
}