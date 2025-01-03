import React from "react";
import './About.scss';
import { Divider, Link, Paper, Typography } from "@mui/material";
import { ILocaleContext, SupportedLocales } from "../../context/locale/Locale";
import { SectionTitle } from "../../components/sectionTitle/SectionTitle";
import { Sections } from "../../types/types";

export interface AboutProps {
  locale: ILocaleContext;
}

function AboutTypography(props: { children: any }) {
  return (
    <Typography className="about-typography">
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
      <Paper className="paper-root"
        elevation={15}>
        <SectionTitle>
          {props.locale.strings.aboutSection.title}
        </SectionTitle>
        <div className="content">
          {props.locale.locale !== SupportedLocales.en_US && (
            <AboutTypography>
              {props.locale.strings.aboutSection.desc}
            </AboutTypography>
          )}
          {props.locale.locale === SupportedLocales.en_US && (
            <>
              <AboutTypography>
                BSc. in Computer Science, minoring in Information Technology from the University of Wisconsin-Superior.
              </AboutTypography>
              {
                InsertLink(
                  props.locale.strings.aboutSection.desc,
                  'Fasetto',
                  'https://www.fasetto.com'
                )}
              <AboutTypography>
                During my time at Fasetto, I worked in numerous different
                technology stacks:
              </AboutTypography>
              <div className="skills-list-h6">
                <span>
                  Linux in embedded devices, .NET Core, Node.js, Next.js, ReactJS, React Native, SQLite, MongoDB, WebRTC,
                  and many other technologies.
                </span>
              </div>
              <AboutTypography>
                I have experience with various non-technical assignments such as; working CES show booths where I
                would be required to ensure booth functionality and demonstrate Fasetto products to the general public.
                Additionally, I was required to attended numerous investor meetings, travel obligations overseas, and working
                with various teams across time zones.
              </AboutTypography>
              <AboutTypography>
                Often times, the deadlines were tight and the teams were
                small - I am very familiar with fluid product design goals, sometimes changing by the hour,
                and prioritizing features to produce an awesome product as effeciently and quickly as needed.
                More information is available in the <Link href={`${Sections.Work}`}>work</Link> section.
                I really loved the challenges and diversity of the start-up culture, and I am excited to start
                a new chapter in my professional career.
              </AboutTypography>
              <div>
                <Typography fontWeight={600} fontSize={'1.12rem'}>Outside of Work</Typography>
                <Divider sx={{ margin: '0.4rem 0rem' }} />
                <AboutTypography>
                  When I am not creating software; I enjoy the outdoors, working on cars, sim racing, and bowling.
                </AboutTypography>
                <AboutTypography>
                  I love spending time at my cabin, with acres of trails and tons of wildlife to watch.
                </AboutTypography>
                <AboutTypography>
                  I have a college degree and used to work as a mechanic; now that I can enjoy it as a hobby
                  again, I like to work on my Camaro z28 and my wife's Miata.
                </AboutTypography>
                <AboutTypography>
                  I have always been a fan of racing games and recently setup a new simulator utilizing Fanatec's
                  CSL DD and Elite pedals. Enjoying Assetto Corsa, Assetto Corsa Competizione, Forza Motorsports,
                  and the Horizon series.
                </AboutTypography>
                <AboutTypography>
                  I have been an avid bowler for about 5 years, having been part of a casual league with my wife
                  for the last 4 years.
                </AboutTypography>
              </div>
            </>
          )}
        </div>
      </Paper>
    </section>
  )
}