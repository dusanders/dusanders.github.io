import { Accordion, Card, CardContent, Link, List, ListItem, Typography } from "@mui/material";
import React from "react";
import { CardTitle } from "./cardTitle";
import { AccordianItem } from "./accordianItem";
import { Utils } from "../../types/types";


export interface ProductSkill {
  skillName: string;
  skillPoints: string[];
}

export interface ProductLink {
  description: string;
  href: string;
}

export interface ProductCardProps {
  productName: string;
  productSummary: JSX.Element;
  skills: ProductSkill[];
  links: ProductLink[]
}

export function ProductCard(props: ProductCardProps) {
  return (
    <Card variant={'elevation'}
      className="card">
      <CardContent className="content">
        <CardTitle
          title={props.productName}
          summary={props.productSummary} />
        {props.skills.map((skill) => (
          <div
            key={Utils.generateGuid()}>
            <AccordianItem
              title={skill.skillName}>
              <List>
                {skill.skillPoints.map((point) => (
                  <ListItem
                    key={Utils.generateGuid()}
                    className="skill-list-item">
                    {point}
                  </ListItem>
                ))}
              </List>
            </AccordianItem>
          </div>
        ))}
        <AccordianItem
          title="See the Product">
          <List>
            {props.links.map((link) => (
              <ListItem
                className="product-link-item"
                key={Utils.generateGuid()}>
                <Typography>
                  {link.description}
                </Typography>
                <Link
                  target={'_blank'}
                  rel={'noopener'}
                  href={link.href}>
                  Link
                </Link>
              </ListItem>
            ))}
          </List>
        </AccordianItem>
      </CardContent>
    </Card>
  )
}