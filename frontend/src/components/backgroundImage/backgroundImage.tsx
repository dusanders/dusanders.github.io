import React, { CSSProperties } from "react";
import './backgroundImage.scss';

export interface BackgroundImageProps {
  children?: any;
  className?: string;
  contentClassName?: string;
  url: string;
  smokeScreen?: boolean
}

export function BackgroundImage(props: BackgroundImageProps) {
  return (
    <div className={`background-image ${props.className || ''}`}
      style={{
        backgroundImage: `url(${props.url})`
      }}>
      <div className={`content-div ` +
        `${props.smokeScreen ? 'smoke-screen ' : ' '}` +
        `${props.contentClassName || ''}`}>
        {props.children}
      </div>
    </div>
  )
}