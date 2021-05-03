export interface Plan {
  planName:string;
  abv?:string;
  description:string;
  difficulty:number;
  categoryName?:string;
  likedUser?:boolean;
}

export interface pageable{
  content:[Plan];
  last: boolean
}
