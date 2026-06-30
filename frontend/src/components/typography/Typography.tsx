import './Typography.scss';

export type TypographyType = "bold"
  | "italic"
  | "light"
  | "link"
  | "faded"
  | "secondary";

export interface TypographyProps {
  children: any;
  variant?: TypographyType;
  className?: string;
  href?: string;
  allCaps?: boolean;
}

export function Typography(props: TypographyProps) {
  if (props.variant === "link") {
    return (
      <a
        target="_blank"
        rel="noopener noreferrer"
        className={`typography link ${props.className || ''} ${props.allCaps ? 'all-caps' : ''}`} href={props.href}>
        {props.children}
      </a>
    )
  } else {
    return (
      <span className={`typography ${props.variant} ${props.className || ''} ${props.allCaps ? 'all-caps' : ''}`}>
        {props.children}
      </span>
    )
  }
}