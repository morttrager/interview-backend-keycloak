import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestionTag, QuestionTag } from 'app/shared/model/question-tag.model';
import { QuestionTagService } from './question-tag.service';
import { QuestionTagComponent } from './question-tag.component';
import { QuestionTagDetailComponent } from './question-tag-detail.component';
import { QuestionTagUpdateComponent } from './question-tag-update.component';

@Injectable({ providedIn: 'root' })
export class QuestionTagResolve implements Resolve<IQuestionTag> {
  constructor(private service: QuestionTagService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionTag> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questionTag: HttpResponse<QuestionTag>) => {
          if (questionTag.body) {
            return of(questionTag.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionTag());
  }
}

export const questionTagRoute: Routes = [
  {
    path: '',
    component: QuestionTagComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionTags',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuestionTagDetailComponent,
    resolve: {
      questionTag: QuestionTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionTags',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuestionTagUpdateComponent,
    resolve: {
      questionTag: QuestionTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionTags',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuestionTagUpdateComponent,
    resolve: {
      questionTag: QuestionTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionTags',
    },
    canActivate: [UserRouteAccessService],
  },
];
