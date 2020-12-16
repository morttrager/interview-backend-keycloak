import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInterviewQuestion, InterviewQuestion } from 'app/shared/model/interview-question.model';
import { InterviewQuestionService } from './interview-question.service';
import { InterviewQuestionComponent } from './interview-question.component';
import { InterviewQuestionDetailComponent } from './interview-question-detail.component';
import { InterviewQuestionUpdateComponent } from './interview-question-update.component';

@Injectable({ providedIn: 'root' })
export class InterviewQuestionResolve implements Resolve<IInterviewQuestion> {
  constructor(private service: InterviewQuestionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInterviewQuestion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((interviewQuestion: HttpResponse<InterviewQuestion>) => {
          if (interviewQuestion.body) {
            return of(interviewQuestion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InterviewQuestion());
  }
}

export const interviewQuestionRoute: Routes = [
  {
    path: '',
    component: InterviewQuestionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InterviewQuestions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InterviewQuestionDetailComponent,
    resolve: {
      interviewQuestion: InterviewQuestionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InterviewQuestions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InterviewQuestionUpdateComponent,
    resolve: {
      interviewQuestion: InterviewQuestionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InterviewQuestions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InterviewQuestionUpdateComponent,
    resolve: {
      interviewQuestion: InterviewQuestionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InterviewQuestions',
    },
    canActivate: [UserRouteAccessService],
  },
];
