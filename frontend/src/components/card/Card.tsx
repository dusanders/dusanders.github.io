import React from "react";
import "./Card.scss";
import { Typography } from "../typography/Typography";
import { Divider } from "../divider/Divider";

export type CardBorderType = "full" | "top" | "left" | "right" | "bottom" | "none";

export interface CardProps {
  children: any;
  id?: string;
  border?: CardBorderType | CardBorderType[];
  title?: string;
  className?: string;
}

export function Card(props: CardProps) {
  const borderClasses = Array.isArray(props.border) ? props.border.map(b => `${b}-border`).join(' ') : `${props.border}-border`;
  return (
    <div id={props.id} className={`card ${props.className || ''} ${props?.border !== 'none' ? borderClasses : ''}`}>
      {props.title && (
        <div className="title">
          <Typography allCaps>
            [{props.title}]
          </Typography>
          <Divider className="title-divider" />
        </div>
      )}
      <div>
        {props.children}
      </div>
    </div>
  )
}