import { Strings } from "../Locale";

export class Strings_KnightsOfNi implements Strings {
  myFirstName: string = "Ni!";
  myLastName: string = "Ni!";
  myJobTitle: string = "Ni!";
  about: string = 'Ni!';
  work: string = 'Ni!';
  passion: string = 'Ni!';
  education: string = 'Ni!';
  chooser = {
    enUs: 'English (US)',
    knightsOfNi: 'Ni!'
  };
  aboutSection = {
    title: 'Ni!',
    desc: 'This is just a fun way to play with React contexts. Using a locale \
    context to provide a switch-able string definition file for consolidated \
    resources used across an entire React app.'
  }
}