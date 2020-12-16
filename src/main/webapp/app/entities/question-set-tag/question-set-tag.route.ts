import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestionSetTag, QuestionSetTag } from 'app/shared/model/question-set-tag.model';
import { QuestionSetTagService } from './question-set-tag.service';
import { QuestionSetTagComponent } from './question-set-tag.component';
import { QuestionSetTagDetailComponent } from './question-set-tag-detail.component';
import { QuestionSetTagUpdateComponent } from './question-set-tag-update.component';

@Injectable({ providedIn: 'root' })
export class QuestionSetTagResolve implements Resolve<IQuestionSetTag> {
  constructor(private service: QuestionSetTagService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionSetTag> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questionSetTag: HttpResponse<QuestionSetTag>) => {
          if (questionSetTag.body) {
            return of(questionSetTag.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionSetTag());
  }
}

export const questionSetTagRoute: Routes = [
  {
    path: '',
    component: QuestionSetTagComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionSetTags',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuestionSetTagDetailComponent,
    resolve: {
      questionSetTag: QuestionSetTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionSetTags',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuestionSetTagUpdateComponent,
    resolve: {
      questionSetTag: QuestionSetTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionSetTags',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuestionSetTagUpdateComponent,
    resolve: {
      questionSetTag: QuestionSetTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionSetTags',
    },
    canActivate: [UserRouteAccessService],
  },
];
