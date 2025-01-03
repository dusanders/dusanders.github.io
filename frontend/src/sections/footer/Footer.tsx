import React from "react";
import './Footer.scss';
import { Link, Typography } from "@mui/material";

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
          href={'https://www.linkedin.com/in/dus-anders/'}>
          LinkedIn
        </Link>
        <Link
          target="_blank"
          rel="noopener"
          href={'https://www.github.com/dusanders'}>
          GitHub
        </Link>
      </div>
    </section>
  )
}