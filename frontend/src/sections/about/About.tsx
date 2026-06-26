import React from "react";
import './About.scss';
import { Divider, Link, Paper, Typography } from "@mui/material";
import { ILocaleContext, SupportedLocales } from "../../context/locale/Locale";
import { SectionTitle } from "../../components/sectionTitle/SectionTitle";
import { Sections } from "../../types/types";
import { StaticAssets } from "../../assets";
import { BackgroundImage } from "../../components/backgroundImage/backgroundImage";
import { Bold } from "../../components/typography/Typography";

export interface AboutProps {
  locale: ILocaleContext;
}

interface AboutTypographyProps {
  children: any;
  variant?: 'heading' | 'body';
}
function AboutTypography(props: AboutTypographyProps) {
  return (
    <Typography className={`about-typography ${props.variant ? props.variant : ''}`}>
      {props.children}
    </Typography>
  )
}

/**
 * Helper function to place a Link element into a Typography string
 * @param fullString Full string to display
 * @param toReplace Token string to replace
 * @param href href to use in Link element
 * @returns Typography element with inserted Link element
 */
function InsertLink(fullString: string, toReplace: string, href: string) {
  const replaceIndex = fullString.indexOf(toReplace);
  return (
    <>
      <AboutTypography>
        {fullString.substring(0, replaceIndex)}
        <Link href={`${href}`}
          target={'_blank'}
          rel={'noopener'}>
          {toReplace}
        </Link>
        {fullString.substring(replaceIndex + toReplace.length)}
      </AboutTypography>
    </>
  );
}

export function AboutSection(props: AboutProps) {

  return (
    <section id="about"
      className="about-root">
          <SectionTitle>
            {props.locale.strings.aboutSection.title}
          </SectionTitle>
          <div className="content">
              <AboutTypography variant="heading">
                Senior Software Engineer | Mobile & IoT Architect | Systems Engineering
                </AboutTypography>
              <Divider className="divider" />
              <Bold>
                What I Do:
              </Bold>
                I architect high-performance cross-platform and native mobile ecosystems (React Native, Kotlin, Java) that interface directly with complex IoT hardware, custom backends (Node.js), and low-latency streaming networks.
              <AboutTypography>

                My Core Passion: I thrive in the middle layer and backend infrastructure—building robust data synchronization layers, handling hardware/device integration, profiling memory management, and engineering custom communication protocols where there is zero margin for error.

                Track Record: Over 10 years of startup execution. Secured a patent for multi-angle video synchronization via custom WebRTC/WebSockets, built BLE/Wi-Fi communication systems for enterprise IoT devices demoed at CES, and modernized legacy event data-pipelines to efficiently process high-concurrency real-time event traffic.
              </AboutTypography>
          </div>
    </section>
  )
}