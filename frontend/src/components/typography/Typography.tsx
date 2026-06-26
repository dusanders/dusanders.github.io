import './Typography.scss';

export interface BoldProps {
  children: any;
}

export function Bold(props: BoldProps) {
  return (
    <span className="bold">
      {props.children}
    </span>
  )
}