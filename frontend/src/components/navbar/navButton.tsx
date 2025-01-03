import { Link, LinkProps } from "@mui/material";
import React from "react";

export interface NavButtonProps extends LinkProps {
  // Nothing - just extend base class
}

export function NavButton(props: NavButtonProps) {
  const prefixClassname = `nav-btn ${props.className || ''}`;
  
  return (
    <Link
      underline={'hover'}
      variant={'inherit'}
      component={'a'}
      color={'primary'}
      {
      ...props
      }
      className={prefixClassname}
    />
  )
}