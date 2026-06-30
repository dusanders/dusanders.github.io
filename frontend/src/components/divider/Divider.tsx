import React from "react";
import "./Divider.scss";

export interface DividerProps {
  className?: string;
}
export function Divider(props: DividerProps) {
  return (
    <div className={`divider ${props.className || ''}`} />
  )
}