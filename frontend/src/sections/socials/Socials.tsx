import React from "react";
import "./Socials.scss";
import { Typography } from "../../components/typography/Typography";
import { ReactComponent as LinkedInIcon } from "../../assets/svgs/linkedin.svg";
import { ReactComponent as GitHubIcon } from "../../assets/svgs/github.svg";
import { ReactComponent as EmailIcon } from "../../assets/svgs/gmail.svg";
import { Card } from "../../components/card/Card";

export interface SocialsProps {
  variant?: "full" | "compact" | "inline";
  className?: string;
}

export function Socials(props: SocialsProps) {
  if (props.variant === "full") {
    return (
      <SocialsFull />
    )
  }
  if (props.variant === "inline") {
    return <SocialsInline />;
  }
  return <SocialsCompact />;
}

function SocialsInline() {
  return (
    <Card title="External_Links" border="none" className="socials">
      <div>
        <div>
          <Typography variant="faded">
            LINKEDIN: <Typography variant="link" href="https://www.linkedin.com/in/dus-anders/">[https://www.linkedin.com/in/dus-anders/]</Typography>
          </Typography>
        </div>
        <div>
          <Typography variant="faded">
            EMAIL: <Typography variant="link" href="mailto:dusanders@gmail.com">[dusanders@gmail.com]</Typography>
          </Typography>
        </div>
        <div>
          <Typography variant="faded">
            GITHUB: <Typography variant="link" href="https://github.com/dusanders">[https://github.com/dusanders]</Typography>
          </Typography>
        </div>
      </div>
    </Card>
  )
}
function SocialsCompact() {
  const iconSize = '1.2rem';
  return (
    <Card
      border="none"
      className="socials"
      title="External_Links">
      <div className="compact">
        <div className="link-div">
          <LinkedInIcon height={iconSize} width={iconSize} className="link-icon" />
          <Typography variant="link" href="https://www.linkedin.com/in/dus-anders/">
            [LinkedIn]
          </Typography>
        </div>
        <div className="link-div">
          <EmailIcon height={iconSize} width={iconSize} className="link-icon" />
          <Typography variant="link" href="mailto:dusanders@gmail.com">
            [Email]
          </Typography>
        </div>
        <div className="link-div">
          <GitHubIcon height={iconSize} width={iconSize} className="link-icon" />
          <Typography variant="link" href="https://github.com/dusanders">
            [GitHub]
          </Typography>
        </div>
      </div>
    </Card>
  )
}

function SocialsFull() {
  return (
    <div className="socials__with-border">
      <div className="title">
        <Typography allCaps className="title">
          {`External Links`}
        </Typography>
      </div>
      <div>
        <table>
          <thead>
            <tr>
              <th>
                <div className="table-heading-text">
                  <Typography variant="faded">
                    Type
                  </Typography>
                </div>
              </th>
              <th>
                <div className="table-heading-text">
                  <Typography variant="faded">
                    Link
                  </Typography>
                </div>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <Typography variant="secondary" className="link-type">
                  [LinkedIn]
                </Typography>
              </td>
              <td>
                <Typography variant="link" href="https://www.linkedin.com/in/dus-anders/">
                  https://www.linkedin.com/in/dus-anders/
                </Typography>
              </td>
            </tr>
            <tr>
              <td>
                <Typography variant="secondary" className="link-type">
                  [Email]
                </Typography>
              </td>
              <td>
                <Typography variant="link" href="mailto:dusanders@gmail.com">
                  dusanders@gmail.com
                </Typography>
              </td>
            </tr>
            <tr>
              <td>
                <Typography variant="secondary" className="link-type">
                  [GitHub]
                </Typography>
              </td>
              <td>
                <Typography variant="link" href="https://github.com/dusanders">
                  https://github.com/dusanders
                </Typography>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  )
}