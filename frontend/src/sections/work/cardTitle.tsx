import { Container, Link, Typography } from "@mui/material";
import React from "react";

export interface CardTitleProps {
  title: string;
  summary: JSX.Element;
  href: string;
}

export function CardTitle(props: CardTitleProps) {
  return (
    <div className="accordian-title">
      <Typography
        fontSize={'3rem'}
        fontWeight={700}
        textTransform={'uppercase'}>
        {props.title}
      </Typography>
      {props.summary}
      <Link
        href={props.href}
        rel={'noopener'}
        target={'_blank'}
        textTransform={'uppercase'}
        fontWeight={400}>
        See Product
      </Link>
    </div>
  )
}