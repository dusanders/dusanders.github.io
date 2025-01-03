import React from "react";
import './Passion.scss';
import { SectionTitle } from "../../components/sectionTitle/SectionTitle";
import { Card, Container, Divider, Link, Typography } from "@mui/material";
import { StaticAssets } from "../../assets";
import { BackgroundImage } from "../../components/backgroundImage/backgroundImage";
import { Masonry } from "@mui/lab";
import { LinkConstants } from "../../types/types";

export interface PassionProps {
  // Nothing at this time
}

interface ProjectCardProps {
  href: string;
  title: JSX.Element;
  body: JSX.Element;
}

function ProjectCard(props: ProjectCardProps) {
  return (
    <Link
      underline={'none'}
      variant={'inherit'}
      component={'a'}
      href={props.href}
      target="_blank"
      rel="noopener"
      className="project-link">
      <Card variant={'elevation'}
        elevation={12}
        className="project-card-root">
        {props.title}
        <Divider />
        {props.body}
      </Card>
    </Link>
  )
}

export function PassionSection(props: PassionProps) {
  return (
    <section id="passion" className="passion-root">
      <BackgroundImage
        smokeScreen
        url={StaticAssets.GlacierNationalParkPanorama}>
        <Container maxWidth={'md'}
          className="content">
          <SectionTitle>
            Passion
          </SectionTitle>
          <Container maxWidth={'lg'}>
            <Typography>
              I love to write software. I have over 30 GitHub repos of various different projects that I have worked on in my free time.
            </Typography>
            <Typography>
              While not all my repos are public, some I reserve for private access where I can play with and test different architectural
              approaches. Some are just simple repos that I use as a template for new projects, some are sandbox repos that I can use
              for playing around with different techniques such as functional programming in TypeScript, different approaches to
              dependency injection / inversion of control / factories / etc.
            </Typography>
          </Container>
          <Masonry columns={{ md: 2, lg: 3 }}
            className="project-masonry">
            <ProjectCard
              href={LinkConstants.ForzaUtils_Android}
              title={(
                <>
                  <Typography
                    className="project-title-text">
                    Forza Utils
                  </Typography>
                  <Typography>
                    (Kotlin Android)
                  </Typography>
                </>
              )}
              body={(
                <Typography
                  className="project-desc-text">
                  Simple Android app written in Kotlin and utilizing another
                  GitHub project (in Java) for providing telemetry data from
                  Forza Motorsport.
                </Typography>
              )} />
            <ProjectCard
              href={LinkConstants.Github_Pages_Portfolio_Repo}
              title={(
                <Typography
                  className="project-title-text">
                  This React source code
                </Typography>
              )}
              body={(
                <Typography
                  className="project-desc-text">
                  Source github for this personal page.
                </Typography>
              )} />
          </Masonry>
        </Container>
      </BackgroundImage>
    </section>
  )
}