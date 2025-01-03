import React from "react";
import './Footer.scss';
import { Container, Link, Typography } from "@mui/material";
import { LinkConstants } from "../../types/types";

export interface FooterProps {
  // Nothing yet
}

export function FooterSection(props: FooterProps) {
  return (
    <section id="contact" className="footer-root">
      <Container>
        <Typography
        fontSize={'small'}>
          This site created by myself, with help from Material UI React package.
        </Typography>
        <Container className="link-container">
          <Link
            target="_blank"
            rel="noopener"
            href={LinkConstants.LinkedInUrl}>
            LinkedIn
          </Link>
          <Link
            target="_blank"
            rel="noopener"
            href={LinkConstants.MyGithubUrl}>
            GitHub
          </Link>
        </Container>
      </Container>
    </section>
  )
}