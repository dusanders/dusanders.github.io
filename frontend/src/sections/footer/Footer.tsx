import React from "react";
import './Footer.scss';
import { Link, Typography } from "@mui/material";
import { LinkConstants } from "../../types/types";

export interface FooterProps {
  // Nothing yet
}

export function FooterSection(props: FooterProps) {
  return (
    <section id="contact" className="footer-root">
      <div className="content">
        <Typography className="copywrite-text">
          This site created by myself, with help from Material UI React package.
        </Typography>
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
      </div>
    </section>
  )
}